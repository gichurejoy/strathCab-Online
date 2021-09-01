package com.example.strathdriver.ui.home;

import com.example.strathdriver.message.Errors;


public interface HomeFragmentListener {
    public interface ShowMessageListener {
        void showErrorMessage(Errors error);
    }
}
