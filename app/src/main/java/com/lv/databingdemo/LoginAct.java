package com.lv.databingdemo;

import android.databinding.Observable;
import android.databinding.OnRebindCallback;
import android.databinding.ViewDataBinding;
import android.transition.TransitionManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.lv.databingdemo.base.BaseActivity;
import com.lv.databingdemo.databinding.ActLoginBinding;
import com.lv.databingdemo.model.Employee;

/**
 * User: 吕勇
 * Date: 2016-09-18
 * Time: 11:51
 * Description:登录
 */
public class LoginAct extends BaseActivity<ActLoginBinding> {
    @Override
    protected int loadLayoutId() {
        return R.layout.act_login;
    }

    @Override
    protected void initData() {
        Employee employee = new Employee("ce", "c");
        mBinding.setEmployee(employee);
        mBinding.setPersenter(new Presenter());
        mBinding.vierwStub.getViewStub().inflate();
        employee.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                Toast.makeText(LoginAct.this, String.valueOf(propertyId), Toast.LENGTH_SHORT).show();
            }
        });
        mBinding.addOnRebindCallback(new OnRebindCallback() {
            @Override
            public boolean onPreBind(ViewDataBinding binding) {
                ViewGroup viewGroup = (ViewGroup) binding.getRoot();
                TransitionManager.beginDelayedTransition(viewGroup);
                return true;
            }
        });
    }

    public class Presenter {
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            mBinding.getEmployee().setFristName(s.toString());
        }

        public void onClick(View view) {
            MainActivity.startMainActivity(LoginAct.this);
        }
        public void onLongClick(Employee employee) {
            Toast.makeText(LoginAct.this, employee.getFristName(), Toast.LENGTH_SHORT).show();
        }

        public void onFocusChange(Employee employee){
            Toast.makeText(LoginAct.this, "onFocusChange", Toast.LENGTH_SHORT).show();
        }

        public void onClickDoBinding(Employee employee) {
            Toast.makeText(LoginAct.this, employee.getLastName(), Toast.LENGTH_SHORT).show();
        }

        public void onCheckChanged(View view,boolean isChecked){
            mBinding.setShowImage(isChecked);
        }

    }


}
