package com.shareholder.voting;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        Set<String> existingVoters = new HashSet<>();
        LocalDate recordDate = LocalDate.now().plusDays(5);

        try {
            Vote vote1 = new Vote("SHAREHOLDER_001", "MEETING_001", "PROPOSAL_A");
            boolean result1 = VoteProcessor.processVote(vote1, existingVoters, recordDate);
            System.out.println("New vote for SHAREHOLDER_001: " + (result1 ? "ACCEPTED" : "REJECTED"));

            Vote vote2 = new Vote("SHAREHOLDER_001", "MEETING_001", "PROPOSAL_B");
            boolean result2 = VoteProcessor.processVote(vote2, existingVoters, recordDate);
            System.out.println("Change vote for SHAREHOLDER_001 (before record date): " + (result2 ? "ACCEPTED" : "REJECTED"));

            Vote vote3 = new Vote("SHAREHOLDER_002", "MEETING_001", "PROPOSAL_C");
            boolean result3 = VoteProcessor.processVote(vote3, existingVoters, recordDate);
            System.out.println("New vote for SHAREHOLDER_002: " + (result3 ? "ACCEPTED" : "REJECTED"));

            LocalDate pastRecordDate = LocalDate.now().minusDays(1);
            Vote vote4 = new Vote("SHAREHOLDER_002", "MEETING_001", "PROPOSAL_A");
            boolean result4 = VoteProcessor.processVote(vote4, existingVoters, pastRecordDate);
            System.out.println("Change vote for SHAREHOLDER_002 (after record date): " + (result4 ? "ACCEPTED" : "REJECTED"));

        } catch (InvalidProposalException e) {
            System.out.println("Error: " + e.getMessage());
        }

        try {
            Vote invalidVote = new Vote("SHAREHOLDER_003", "MEETING_001", "PROPOSAL_Z");
            VoteProcessor.processVote(invalidVote, existingVoters, recordDate);
        } catch (InvalidProposalException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
