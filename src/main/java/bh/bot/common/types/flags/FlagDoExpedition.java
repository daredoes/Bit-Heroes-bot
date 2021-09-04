package bh.bot.common.types.flags;

import bh.bot.app.AbstractApplication;
import bh.bot.app.AfkApp;

public class FlagDoExpedition extends FlagPattern.NonParamFlag {

	@Override
	public String getName() {
		return "expedition";
	}

    @Override
    public String getDescription() {
        return "Auto doing Expedition";
    }

    @Override
    public boolean isGlobalFlag() {
        return false;
    }

    @Override
    public boolean internalCheckIsSupportedByApp(AbstractApplication instance) {
        return instance instanceof AfkApp;
    }
}