
package com.whty.zjrcpos.action;

import com.wizarpos.mvc.base.AbstractAction;
import com.wizarpos.mvc.base.ActionCallback;

import java.util.Map;

public class ActionModel extends AbstractAction {
    protected ActionCallback mCallback;

    void setParameter(ActionCallback callback) {
        if (mCallback == null) {
            this.mCallback = callback;
        }
    }

    @Override
    protected void doBefore(Map<String, Object> param, ActionCallback callback) {
        super.doBefore(param, callback);
        setParameter(callback);
    }

}
