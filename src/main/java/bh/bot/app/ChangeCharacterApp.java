package bh.bot.app;

import bh.bot.Main;
import bh.bot.common.Configuration;
import bh.bot.common.Telegram;
import bh.bot.common.exceptions.NotSupportedException;
import bh.bot.common.types.annotations.AppMeta;
import bh.bot.common.types.annotations.RequireSingleInstance;
import bh.bot.common.types.images.BwMatrixMeta;
import bh.bot.common.utils.ColorizeUtil;
import bh.bot.common.utils.InteractionUtil;
import bh.bot.common.utils.ThreadUtil;

import java.awt.*;
import java.util.concurrent.atomic.AtomicBoolean;

import static bh.bot.common.Log.*;
import static bh.bot.common.utils.InteractionUtil.Mouse.moveCursor;
import static bh.bot.common.utils.ThreadUtil.sleep;

@AppMeta(code = "character", name = "Change Character", displayOrder = 1, argType = "number", argAsk = "What character slot do you want to pick?", argDefault = "1", argRequired = true)
@RequireSingleInstance
public class ChangeCharacterApp extends AbstractApplication {

    @Override
    protected void internalRun(String[] args) {
        int arg;
        try {
            arg = Integer.parseInt(args[0]);
        } catch (ArrayIndexOutOfBoundsException | NumberFormatException ex) {
            info(getHelp());
            arg = readInputLoopCount("What character slot do you want to pick?");
        }

        final int characterSlot = arg;
        info("Character in slot %3d ", characterSlot);
        AtomicBoolean masterSwitch = new AtomicBoolean(false);
        ThreadUtil.waitDone(
                () -> doLoopClickImage(characterSlot, masterSwitch),
				() -> internalDoSmallTasks( //
						masterSwitch, //
						SmallTasks //
								.builder() //
								.clickTalk() //
								.clickDisconnect() //
								.reactiveAuto() //
								.autoExit() //
								.detectChatboxDirectMessage() //
								.build() //
				), //
                () -> doCheckGameScreenOffset(masterSwitch)
        );
        Telegram.sendMessage("Stopped", false);
    }

    private void doLoopClickImage(int characterSlot, AtomicBoolean masterSwitch) {
        info(ColorizeUtil.formatInfo, "\n\nStarting Character Change");
        Main.warningSupport();
        try {
            final int mainLoopInterval = Configuration.Interval.Loop.getMainLoopInterval(getDefaultMainLoopInterval());

            moveCursor(new Point(100, 500));
            boolean characterLoaded = false;
            while (!characterLoaded && !masterSwitch.get()) {
                sleep(mainLoopInterval);
                if (clickImage(BwMatrixMeta.Metas.Character.Labels.characterSelect)) {
                    info("Character Select Menu Loaded");
                    if (characterSlot == 0) {
                        InteractionUtil.Mouse.mouseMoveAndClickAndHide(new Point(250, 200));
                    } else if (characterSlot == 1) {
                        InteractionUtil.Mouse.mouseMoveAndClickAndHide(new Point(375, 200));
                    } else if (characterSlot == 2) {
                        InteractionUtil.Mouse.mouseMoveAndClickAndHide(new Point(500, 200));
                    } else {
                        throw new NotSupportedException("Cannot select character in this slot.");
                    }
                    sleep(2_000);
                    if (clickImage(BwMatrixMeta.Metas.Character.Labels.confirm)) {
                        sleep(20_000);
                        characterLoaded = true;
                    }
                } else {
                    throw new NotSupportedException("Couldn't select character in the desired slot.");
                }
            }
            masterSwitch.set(true);
        } catch (Exception ex) {
            ex.printStackTrace();
            Telegram.sendMessage("Error occurs during execution: " + ex.getMessage(), true);
            masterSwitch.set(true);
        }
    }

    @Override
    protected String getUsage() {
        return "<slot>";
    }

    @Override
    protected String getDescription() {
        return "Change character button. Used to switch between character slots";
    }

    @Override
    protected String getLimitationExplain() {
        return "This function only supports clicking the Character slots #1 #2 and #3 right now. Feel free to add support for more yourself.";
    }

    @Override
    protected int getDefaultMainLoopInterval() {
        return 2_000;
    }
}
