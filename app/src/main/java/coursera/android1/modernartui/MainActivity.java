package coursera.android1.modernartui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.SeekBar;


public class MainActivity extends ActionBarActivity {

    private LinearLayout mArt;
    private DialogFragment mDialog;
    private static final String URL = "http://www.moma.org/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final View box1 = (View) findViewById(R.id.box1);
        final View box2 = (View) findViewById(R.id.box2);
        final View box3 = (View) findViewById(R.id.box3);
        // box4 is white and will stay that way
        final View box5 = (View) findViewById(R.id.box5);

        final int originColor1 = ((ColorDrawable) box1.getBackground()).getColor();
        final int originColor2 = ((ColorDrawable) box2.getBackground()).getColor();
        final int originColor3 = ((ColorDrawable) box3.getBackground()).getColor();
        // box4 is white and will stay that way
        final int originColor5 = ((ColorDrawable) box5.getBackground()).getColor();

        mArt = (LinearLayout) findViewById(R.id.art);
        SeekBar seekBar = (SeekBar) findViewById(R.id.seekBar);
        seekBar.setMax(150);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                setBoxColorByProgress(box1, originColor1, progress);
                setBoxColorByProgress(box2, originColor2, progress);
                setBoxColorByProgress(box3, originColor3, progress);
                setBoxColorByProgress(box5, originColor5, progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }

            private void setBoxColorByProgress(View box, int originalColor, int progressChange) {
                float[] hsvColor = new float[3];
                Color.colorToHSV(originalColor, hsvColor);
                hsvColor[0] += progressChange;
                box.setBackgroundColor(Color.HSVToColor(hsvColor));
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_more_info) {
            mDialog = MoreInfoDialogFragment.newInstance();
            mDialog.show(getFragmentManager(), "MoreInfo");

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public static class MoreInfoDialogFragment extends DialogFragment {

        public static MoreInfoDialogFragment newInstance() {
            return new MoreInfoDialogFragment();
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            return new AlertDialog.Builder(getActivity())
                    .setMessage("Inspired by the works of artists such as Piet Mondrian and Ben Nicholson\n\n" +
                                "Click below to learn more!")
                    .setCancelable(false)
                    .setPositiveButton("Visit MOMA",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Uri webPage = Uri.parse(URL);
                                    Intent webIntent = new Intent(Intent.ACTION_VIEW, webPage);
                                    startActivity(webIntent);
                                }
                            })
                    .setNegativeButton("Not Now",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            }).create();
        }
    }
}
