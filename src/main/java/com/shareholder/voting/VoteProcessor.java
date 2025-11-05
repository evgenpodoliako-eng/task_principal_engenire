package com.shareholder.voting;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;

public class VoteProcessor {
    private static final Map<String, Set<String>> VALID_PROPOSALS = new HashMap<>();

    static {
        Set<String> meeting1Proposals = new HashSet<>();
        meeting1Proposals.add("PROPOSAL_A");
        meeting1Proposals.add("PROPOSAL_B");
        meeting1Proposals.add("PROPOSAL_C");
        VALID_PROPOSALS.put("MEETING_001", meeting1Proposals);

        Set<String> meeting2Proposals = new HashSet<>();
        meeting2Proposals.add("PROPOSAL_X");
        meeting2Proposals.add("PROPOSAL_Y");
        VALID_PROPOSALS.put("MEETING_002", meeting2Proposals);
    }

    public static boolean processVote(Vote vote, Set<String> existingVoters, LocalDate recordDate)
            throws InvalidProposalException {

        if (!isValidProposal(vote.getMeetingId(), vote.getProposalId())) {
            throw new InvalidProposalException(
                "Invalid proposal: " + vote.getProposalId() + " for meeting: " + vote.getMeetingId()
            );
        }

        boolean isNewVote = !existingVoters.contains(vote.getShareholderId());

        if (isNewVote) {
            existingVoters.add(vote.getShareholderId());
            return true;
        }

        LocalDate currentDate = LocalDate.now();
        if (currentDate.isBefore(recordDate)) {
            return true;
        }

        return false;
    }

    private static boolean isValidProposal(String meetingId, String proposalId) {
        Set<String> proposals = VALID_PROPOSALS.get(meetingId);
        return proposals != null && proposals.contains(proposalId);
    }
}
