package com.droidrank.tictactoe;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity{

    int c[][];
    int i, j = 0;
    Button b[][];
    boolean playerTurn = false;
    Button restart;
    TextView result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setBoard();
        /**
         * Restarts the game
         */
        restart = (Button) findViewById(R.id.bt_restart_game);
        restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle(getResources().getString(R.string.app_name));
                builder.setMessage(getResources().getString(R.string.restart_message));
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        setBoard();
                        playerTurn = false;
                        result.setText("");
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(MainActivity.this, "Cancel", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.create();
                builder.show();
            }
        });
    }

    private void setBoard() {
        b = new Button[4][4];
        c = new int[4][4];


        result = (TextView) findViewById(R.id.tv_show_result);

        b[1][3] = (Button) findViewById(R.id.bt_block1);
        b[1][2] = (Button) findViewById(R.id.bt_block2);
        b[1][1] = (Button) findViewById(R.id.bt_block3);
        b[2][3] = (Button) findViewById(R.id.bt_block4);
        b[2][2] = (Button) findViewById(R.id.bt_block5);
        b[2][1] = (Button) findViewById(R.id.bt_block6);
        b[3][3] = (Button) findViewById(R.id.bt_block7);
        b[3][2] = (Button) findViewById(R.id.bt_block8);
        b[3][1] = (Button) findViewById(R.id.bt_block9);


        for (i = 1; i <= 3; i++) {
            for (j = 1; j <= 3; j++)
                c[i][j] = 2;
        }

        for (i = 1; i <= 3; i++) {
            for (j = 1; j <= 3; j++) {
                b[i][j].setOnClickListener(new MyClickListener(i, j));
                if(!b[i][j].isEnabled()) {
                    b[i][j].setText(" ");
                    b[i][j].setEnabled(true);
                }
            }
        }
    }

    class MyClickListener implements View.OnClickListener {
        int x;
        int y;

        public MyClickListener(int x, int y) {
            this.x = x;
            this.y = y;
        }
        public void onClick(View view) {
            if (b[x][y].isEnabled()) {
                restart.setText(getResources().getString(R.string.restart_button_text_in_middle_of_game));
                b[x][y].setEnabled(false);
                b[x][y].setText(playerTurn ? getResources().getString(R.string.player_2_move) : getResources().getString(R.string.player_1_move));
                c[x][y] = playerTurn ?  0 : 1;
                result.setText("");
                if (!checkBoard()) {
                    playerTurn = playerTurn ? false: true;
                }
            }
        }
    }

    private boolean checkBoard() {
        boolean gameOver = false;
        if ((c[1][1] == 0 && c[2][2] == 0 && c[3][3] == 0)
                || (c[1][3] == 0 && c[2][2] == 0 && c[3][1] == 0)
                || (c[1][2] == 0 && c[2][2] == 0 && c[3][2] == 0)
                || (c[1][3] == 0 && c[2][3] == 0 && c[3][3] == 0)
                || (c[1][1] == 0 && c[1][2] == 0 && c[1][3] == 0)
                || (c[2][1] == 0 && c[2][2] == 0 && c[2][3] == 0)
                || (c[3][1] == 0 && c[3][2] == 0 && c[3][3] == 0)
                || (c[1][1] == 0 && c[2][1] == 0 && c[3][1] == 0)) {
            result.setText(getResources().getString(R.string.player_2_wins));

            gameOver = true;
        } else if ((c[1][1] == 1 && c[2][2] == 1 && c[3][3] == 1)
                || (c[1][3] == 1 && c[2][2] == 1 && c[3][1] == 1)
                || (c[1][2] == 1 && c[2][2] == 1 && c[3][2] == 1)
                || (c[1][3] == 1 && c[2][3] == 1 && c[3][3] == 1)
                || (c[1][1] == 1 && c[1][2] == 1 && c[1][3] == 1)
                || (c[2][1] == 1 && c[2][2] == 1 && c[2][3] == 1)
                || (c[3][1] == 1 && c[3][2] == 1 && c[3][3] == 1)
                || (c[1][1] == 1 && c[2][1] == 1 && c[3][1] == 1)) {
            result.setText(getResources().getString(R.string.player_1_wins));
            gameOver = true;
        } else {
            boolean empty = false;
            for(i=1; i<=3; i++) {
                for(j=1; j<=3; j++) {
                    if(c[i][j]==2) {
                        empty = true;
                        break;
                    }
                }
            }
            if(!empty) {
                gameOver = true;
                result.setText(getResources().getString(R.string.draw));
            }
        }
        if (gameOver){
            restart.setText(getResources().getString(R.string.restart_button_text_initially));
        }
        return gameOver;
    }
}
