package com.shareholder.voting;

public class Vote {
    private String shareholderId;
    private String meetingId;
    private String proposalId;

    public Vote(String shareholderId, String meetingId, String proposalId) {
        this.shareholderId = shareholderId;
        this.meetingId = meetingId;
        this.proposalId = proposalId;
    }

    public String getShareholderId() {
        return shareholderId;
    }

    public void setShareholderId(String shareholderId) {
        this.shareholderId = shareholderId;
    }

    public String getMeetingId() {
        return meetingId;
    }

    public void setMeetingId(String meetingId) {
        this.meetingId = meetingId;
    }

    public String getProposalId() {
        return proposalId;
    }

    public void setProposalId(String proposalId) {
        this.proposalId = proposalId;
    }
}
