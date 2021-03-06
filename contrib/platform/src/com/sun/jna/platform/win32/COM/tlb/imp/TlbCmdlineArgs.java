/* Copyright (c) 2013 Tobias Wolf, All Rights Reserved
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 */
package com.sun.jna.platform.win32.COM.tlb.imp;

import java.util.Hashtable;

public class TlbCmdlineArgs extends Hashtable<String, String> implements
        TlbConst {

    public TlbCmdlineArgs(String[] args) {
        this.readCmdArgs(args);
    }

    public int getIntParam(String key) {
        String param = this.getParam(key);
        return new Integer(param).intValue();
    }
    
    public String getParam(String key) {
        String param = this.get(key);
        if(param == null)
            throw new TlbParameterNotFoundException("Commandline parameter not found: " + key);
        
        return param;
    }
    
    private void readCmdArgs(String[] args) {
        if(args.length < 2)
            this.showCmdHelp();
        
        for (int i = 0; i < args.length; i++) {
            String cmd = args[i];
            if (cmd.startsWith("-")) {
                this.put(args[i].substring(1), args[i + 1]);
            }
        }
    }

    public boolean isTlbFile() {
        return this.containsKey(CMD_ARG_TYPELIB_FILE);
    }

    public boolean isTlbId() {
        return this.containsKey(CMD_ARG_TYPELIB_ID);
    }
    
    public void showCmdHelp() {
        String helpStr = "usage: TlbImp [-tlb.id -tlb.major.version -tlb.minor.version] [-tlb.file]" + CRCR +
                         "options:" + CR +
                         "-tlb.id               The guid of the type library." + CR +
                         "-tlb.major.version    The major version of the type library." + CR +
                         "-tlb.minor.version    The minor version of the type library." + CR +
                         "-tlb.file             The file name containing the type library." + CRCR +                         
                         "samples:" + CR +
                         "Microsoft Shell Controls And Automation:" + CR +
                         "-tlb.file shell32.dll" + CR +
                         "-tlb.id {50A7E9B0-70EF-11D1-B75A-00A0C90564FE} -tlb.major.version 1 -tlb.minor.version 0" + CRCR +
                         "Microsoft Word 12.0 Object Library:" + CR +
                         "-tlb.id {00020905-0000-0000-C000-000000000046} -tlb.major.version 8 -tlb.minor.version 4" + CRCR;
                         
        System.out.println(helpStr);
        System.exit(0);
    }
}
