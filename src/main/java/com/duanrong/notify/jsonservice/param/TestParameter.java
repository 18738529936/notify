package com.duanrong.notify.jsonservice.param;

public class TestParameter extends Parameter {

    /**
     *
     */
    private static final long serialVersionUID = -6427264021593569890L;

    private String loanId;

    public String getLoanId() {
        return loanId;
    }

    public void setLoanId(String loanId) {
        this.loanId = loanId;
    }

    @Override
    public String toString() {
        return "TestParameter [loanId=" + loanId + "]";
    }

}