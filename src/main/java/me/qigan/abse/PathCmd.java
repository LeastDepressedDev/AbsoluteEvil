package me.qigan.abse;

import me.qigan.abse.pathing.Path;
import me.qigan.abse.sync.Sync;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.BlockPos;

public class PathCmd extends CommandBase {
    @Override
    public String getCommandName() {
        return "pth";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/pth <smth>";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
        if (args.length > 0) {
            if (args[0].equalsIgnoreCase("go") && args.length > 3) {
                Index.MOVEMENT_CONTROLLER.go(new Path(Sync.playerPosAsBlockPos(),
                        new BlockPos(
                                Integer.parseInt(args[1]),
                                Integer.parseInt(args[2]),
                                Integer.parseInt(args[3])
                        )).build());
            }
        }
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }
}
