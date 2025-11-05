package com.shareholder.voting;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class VoteProcessorTest {
    private Set<String> existingVoters;
    private LocalDate futureRecordDate;
    private LocalDate pastRecordDate;

    @BeforeEach
    public void setUp() {
        existingVoters = new HashSet<>();
        futureRecordDate = LocalDate.now().plusDays(5);
        pastRecordDate = LocalDate.now().minusDays(1);
    }

    @Test
    public void testNewVoteIsAccepted() throws InvalidProposalException {
        Vote vote = new Vote("SHAREHOLDER_001", "MEETING_001", "PROPOSAL_A");
        boolean result = VoteProcessor.processVote(vote, existingVoters, futureRecordDate);

        assertTrue(result);
        assertTrue(existingVoters.contains("SHAREHOLDER_001"));
    }

    @Test
    public void testVoteChangeBeforeRecordDateIsAccepted() throws InvalidProposalException {
        existingVoters.add("SHAREHOLDER_001");

        Vote vote = new Vote("SHAREHOLDER_001", "MEETING_001", "PROPOSAL_B");
        boolean result = VoteProcessor.processVote(vote, existingVoters, futureRecordDate);

        assertTrue(result);
    }

    @Test
    public void testVoteChangeAfterRecordDateIsRejected() throws InvalidProposalException {
        existingVoters.add("SHAREHOLDER_001");

        Vote vote = new Vote("SHAREHOLDER_001", "MEETING_001", "PROPOSAL_A");
        boolean result = VoteProcessor.processVote(vote, existingVoters, pastRecordDate);

        assertFalse(result);
    }

    @Test
    public void testVoteChangeOnRecordDateIsRejected() throws InvalidProposalException {
        existingVoters.add("SHAREHOLDER_001");
        LocalDate todayRecordDate = LocalDate.now();

        Vote vote = new Vote("SHAREHOLDER_001", "MEETING_001", "PROPOSAL_A");
        boolean result = VoteProcessor.processVote(vote, existingVoters, todayRecordDate);

        assertFalse(result);
    }

    @Test
    public void testInvalidProposalThrowsException() {
        Vote vote = new Vote("SHAREHOLDER_001", "MEETING_001", "INVALID_PROPOSAL");

        assertThrows(InvalidProposalException.class, () -> {
            VoteProcessor.processVote(vote, existingVoters, futureRecordDate);
        });
    }

    @Test
    public void testInvalidMeetingThrowsException() {
        Vote vote = new Vote("SHAREHOLDER_001", "INVALID_MEETING", "PROPOSAL_A");

        assertThrows(InvalidProposalException.class, () -> {
            VoteProcessor.processVote(vote, existingVoters, futureRecordDate);
        });
    }

    @Test
    public void testValidProposalForMeeting002() throws InvalidProposalException {
        Vote vote = new Vote("SHAREHOLDER_001", "MEETING_002", "PROPOSAL_X");
        boolean result = VoteProcessor.processVote(vote, existingVoters, futureRecordDate);

        assertTrue(result);
        assertTrue(existingVoters.contains("SHAREHOLDER_001"));
    }

    @Test
    public void testMultipleNewVotesFromDifferentShareholders() throws InvalidProposalException {
        Vote vote1 = new Vote("SHAREHOLDER_001", "MEETING_001", "PROPOSAL_A");
        Vote vote2 = new Vote("SHAREHOLDER_002", "MEETING_001", "PROPOSAL_B");
        Vote vote3 = new Vote("SHAREHOLDER_003", "MEETING_001", "PROPOSAL_C");

        assertTrue(VoteProcessor.processVote(vote1, existingVoters, futureRecordDate));
        assertTrue(VoteProcessor.processVote(vote2, existingVoters, futureRecordDate));
        assertTrue(VoteProcessor.processVote(vote3, existingVoters, futureRecordDate));

        assertEquals(3, existingVoters.size());
    }

    @Test
    public void testExistingVotersSetIsUpdatedForNewVote() throws InvalidProposalException {
        assertEquals(0, existingVoters.size());

        Vote vote = new Vote("SHAREHOLDER_001", "MEETING_001", "PROPOSAL_A");
        VoteProcessor.processVote(vote, existingVoters, futureRecordDate);

        assertEquals(1, existingVoters.size());
        assertTrue(existingVoters.contains("SHAREHOLDER_001"));
    }

    @Test
    public void testExistingVotersSetNotChangedForVoteChange() throws InvalidProposalException {
        existingVoters.add("SHAREHOLDER_001");
        assertEquals(1, existingVoters.size());

        Vote vote = new Vote("SHAREHOLDER_001", "MEETING_001", "PROPOSAL_B");
        VoteProcessor.processVote(vote, existingVoters, futureRecordDate);

        assertEquals(1, existingVoters.size());
    }
}
