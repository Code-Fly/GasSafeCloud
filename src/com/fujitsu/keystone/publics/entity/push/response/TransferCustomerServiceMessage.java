/**
 *
 */
package com.fujitsu.keystone.publics.entity.push.response;

/**
 * @author Barrie
 */
public class TransferCustomerServiceMessage extends BaseMessage {
    private TransInformation TransInfo;

    public TransInformation getTransInfo() {
        return TransInfo;
    }

    public void setTransInfo(TransInformation transInfo) {
        TransInfo = transInfo;
    }
}
