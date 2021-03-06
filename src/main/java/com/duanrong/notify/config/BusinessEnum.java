package com.duanrong.notify.config;

/**
 * 业务类型
 *
 * @author xiaoshuo
 */
public enum BusinessEnum {

    //开户
    create_account,

    //管理员转入
    transfer_in,

    //管理员转出
    transfer_out,

    //管理员冻结
    freeze,

    //管理员解冻
    unfreeze,

    //充值
    recharge,

    //充值手续费
    fee,

    //线下充值
    recharge_line,

    //提现
    withdraw_cash,

    //退款
    refund,

    //直接转账
    transfer,

    //投资
    invest,

    //流标
    bidders,

    //放款
    give_money_to_borrower,

    //还款
    repay,

    //天天赚购买
    demand_in,

    //天天赚赎回
    demand_out,

    //补息
    allowance,

    //红包奖励
    reward,

    //平台转账
    pt_transfer


}
