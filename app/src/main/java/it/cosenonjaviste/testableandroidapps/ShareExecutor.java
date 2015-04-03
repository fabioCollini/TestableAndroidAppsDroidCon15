package it.cosenonjaviste.testableandroidapps;

import android.content.Context;
import android.content.Intent;

public class ShareExecutor {
    private Context context;

    public ShareExecutor(Context context) {
        this.context = context;
    }

    public void startSendActivity(String title, String body) {
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
