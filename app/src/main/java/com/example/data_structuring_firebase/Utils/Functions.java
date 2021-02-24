package com.example.data_structuring_firebase.Utils;

import com.example.data_structuring_firebase.Data.Models.Status;

public class Functions {
    public static String ParseBoardStatus(Status status)
    {
        switch (status)
        {
            case STATUS_IN_PROGRESS: return "Pending";
            case STATUS_COMPLETE: return "Complete";
            case STATUS_NOT_STARTED: return "Not Started";
            default:return "N/A";
        }
    }
}
