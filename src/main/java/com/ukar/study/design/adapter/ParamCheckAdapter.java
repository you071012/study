package com.ukar.study.design.adapter;

public class ParamCheckAdapter implements ParamCheck{

    private Verify verify;

    public ParamCheckAdapter(Verify verify) {
        this.verify = verify;
    }

    @Override
    public void check() {
        VerifyResult result = verify.doVerify();
        if(result.isVerify()){
            System.out.println("验证通过");
        }else {
            System.out.println("验证不通过，msg：" + result.getMsg());
        }
    }
}
