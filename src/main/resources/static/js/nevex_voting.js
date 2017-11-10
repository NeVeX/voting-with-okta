

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
    var teamVotedBannerId = "#thanks-for-voting-"+teamId;
    $(teamVotedBannerId).fadeIn(600);

    setInterval(function () {
        $(teamVotedBannerId).fadeOut(1000);
    }, 5000);
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
    var resourceName = $("meta[name='_votingResourceName']").attr("content");
    getAndUpdateUsersVotes(resourceName);
});