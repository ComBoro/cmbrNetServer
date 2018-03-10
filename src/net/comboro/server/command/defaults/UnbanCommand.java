/*
 * This file is part of Fusster.
 *	
 * Fusster Copyright (C) ComBoro
 *
 * Fusster is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.

 * Fusster is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Fusster.  If not, see <http://www.gnu.org/licenses/>.
 */

package net.comboro.server.command.defaults;

import net.comboro.server.Server;
import net.comboro.server.command.CommandSender;

public class UnbanCommand extends DefaultCommand {

    public UnbanCommand() {
        super("UnbanCommand", "Unbans an IP", "unban <IP>","unban 127.0.0.1");
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (args.length == 0)
            return false;
        String ip = args[0].trim();
        Server.unban(ip);
        sender.sendMessage("Unbanned " + ip + ".");
        return true;
    }

}
