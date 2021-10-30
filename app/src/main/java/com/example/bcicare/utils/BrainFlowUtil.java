package com.example.bcicare.utils;

import android.util.Log;

import java.io.IOException;
import java.util.Arrays;

import brainflow.BoardShim;
import brainflow.BrainFlowError;
import brainflow.BrainFlowInputParams;
import brainflow.LogLevels;

public class BrainFlowUtil {

    private static final String TAG = "BrainFlowGetDataUtil";

    private BrainFlowInputParams inputParams;
    private BoardShim boardShim;
    private String ip_address = "192.168.4.1";
    private int ip_port = 2195;
    private int board_id = 5;
    private static boolean isPrepare = false;


    public BrainFlowUtil() {
        inputParams = new BrainFlowInputParams();
        inputParams.set_ip_address(ip_address);
        inputParams.set_ip_port(ip_port);
    }

    public BrainFlowUtil(BrainFlowInputParams inputParams) {
        this.inputParams = inputParams;
    }


    public void prepareSession() {
        try {
            BoardShim.enable_board_logger();
            boardShim = new BoardShim(board_id, inputParams);
            boardShim.prepare_session();
            isPrepare = true;
        } catch (Exception e) {
            isPrepare = false;
            e.printStackTrace();
        }
    }

    public boolean isPrepare(){
        return isPrepare;
    }

    public void startStream() throws Exception {
        boardShim.start_stream();
    }

    public void getEEGData() throws Exception {

        // BoardShim.log_message(LogLevels.LEVEL_INFO.get_code(), "Start sleeping in the main thread");
        // Thread.sleep(5000);
        // boardShim.stop_stream();
        System.out.println(boardShim.get_board_data_count());
        double[][] data = boardShim.get_current_board_data(1280); // doesnt flush it from ring buffer
        // double[][] data = board_shim.get_board_data (); // get all data and flush

        int[] eeg_channels = BoardShim.get_eeg_channels(board_id); // eeg 通道

        int time_stamp_channel = BoardShim.get_timestamp_channel(board_id); // timestamp

        System.out.println(Arrays.toString(data[time_stamp_channel]));

        for (int i = 0; i < eeg_channels.length; i++) {
            System.out.println(Arrays.toString(data[eeg_channels[i]]));
            Log.d(TAG, "getData: eeg_data " + i + " length " + data[eeg_channels[i]].length);

        }
        Log.d(TAG, "getData: eeg_data.length " + eeg_channels.length);

    }


    public void stopStream() throws Exception{
        boardShim.stop_stream();
    }


    public void releaseSession() {
        try {
            boardShim.release_session();
            isPrepare = false;
        } catch (Exception e){
            isPrepare = true;
            e.printStackTrace();
        }
    }

}