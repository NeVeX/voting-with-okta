

function onVoteButtonClicked(element) {

    var voteSubmitElement = $(element);
    var teamId = voteSubmitElement.attr('data-teamId');
    var resourceName = voteSubmitElement.attr('data-resourceName');
    var voteType = voteSubmitElement.attr('data-vote-type');
    console.log("Voting for Team ["+teamId+"] for vote-type ["+voteType+"]");

    placeVote(resourceName, teamId, voteType);
}


function placeVote(resourceName, teamId, voteType) {

    var scope = this;

    scope.voteRequest = { };
    scope.voteRequest[voteType] = true;

    $.ajax({
        type: "POST",
        url: "api/"+resourceName+"/"+teamId+"/votes",
        headers: getHeadersForApiPostCall(),
        data: JSON.stringify( scope.voteRequest  ),
        success: function(data) {
            console.log("Successfully voted");
            showYouVotedBanner(teamId);
            updateUserVotes(data);
        },
        error: function(error) {
            console.log("Error: "+ JSON.stringify(error));
            alert("Hmm, there was an error");
        }
    });
}

function getHeadersForApiPostCall() {
    var csrfToken = $("meta[name='_csrf']").attr("content");
    // var csrfHeader = $("meta[name='_csrf_header']").attr("content");
    return { "Content-Type": "application/json", "X-CSRF-TOKEN": csrfToken };
}

function showYouVotedBanner(teamId) {
    var thanksForVotingText = "#thanks-for-voting-text";
    $(thanksForVotingText).html("Thanks for voting!");
    $(thanksForVotingText).show();
    // $(thanksForVotingText).fadeIn(600);
    //
    // setInterval(function () {
    //     $(thanksForVotingText).fadeOut(1000);
    // }, 5000);
}

function updateUserVotes(userVotes) {
    if ( ! userVotes ) { return; }
    if ( userVotes.grand_prize_team_name) {
        $("#user-vote-grand-prize").text(userVotes.grand_prize_team_name);
    }
    if ( userVotes.most_creative_team_name) {
        $("#user-vote-most-creative").text(userVotes.most_creative_team_name);
    }
    if ( userVotes.most_impactful_team_name) {
        $("#user-vote-most-impactful").text(userVotes.most_impactful_team_name);
    }

    // update the buttons too
    var allVoteButtons = $('input[id^="vote-button-"]');
    if ( allVoteButtons && allVoteButtons.length > 0) {
        for ( var index = 0; index < allVoteButtons.length; index++) {
            var button = $(allVoteButtons[index]);
            var teamId = button.attr('data-teamId');
            var voteType = button.attr('data-vote-type');
            button.prop("disabled", false);
            if ( userVotes.grand_prize_team_id && teamId == userVotes.grand_prize_team_id && voteType == 'grand_prize') {
                button.prop("disabled", true);
            }
            if ( userVotes.most_creative_team_id && teamId == userVotes.most_creative_team_id && voteType == 'most_creative') {
                button.prop("disabled", true);
            }
            if ( userVotes.most_impactful_team_id && teamId == userVotes.most_impactful_team_id && voteType == 'most_impactful') {
                button.prop("disabled", true);
            }
        }
    }
}

function getAndUpdateUsersVotes(resourceName) {
    $.ajax({
        type: "GET",
        url: "api/"+resourceName+"/votes",
        success: function(userVotes) {
            console.log("Successfully got users votes "+JSON.stringify(userVotes));
            updateUserVotes(userVotes);
        },
        error: function(error) {
            console.log("Error: "+ JSON.stringify(error));
            alert("Hmm, there was an error");
        }
    });
}

$( document ).ready(function() {
    findAndUpdateUsersVotes();
});

function findAndUpdateUsersVotes() {
    var resourceName = $("meta[name='_votingResourceName']").attr("content");
    getAndUpdateUsersVotes(resourceName);
}