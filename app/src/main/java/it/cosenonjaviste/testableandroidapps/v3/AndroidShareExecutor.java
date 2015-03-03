package it.cosenonjaviste.testableandroidapps.v3;

import android.content.Context;
import android.content.Intent;

import it.cosenonjaviste.testableandroidapps.R;

public class AndroidShareExecutor implements ShareExecutor {

    private Context context;

    public AndroidShareExecutor(Context context) {
        this.context = context;
    }

    @Override public void startSendActivity(String title, String body) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_TITLE, title);
        intent.putExtra(Intent.EXTRA_TEXT, body);
        intent.setType("text/plain");
        Intent chooserIntent = Intent.createChooser(intent, context.getResources().getText(R.string.share));
        chooserIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(chooserIntent);
    }
}
