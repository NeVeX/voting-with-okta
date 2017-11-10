

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

    var csrfToken = $("meta[name='_csrf']").attr("content");
    // var csrfHeader = $("meta[name='_csrf_header']").attr("content");

    $.ajax({
        type: "POST",
        url: "api/"+resourceName+"/"+teamId+"/votes",
        headers: { "Content-Type": "application/json", "X-CSRF-TOKEN": csrfToken },
        data: JSON.stringify( scope.voteRequest  ),
        success: function(data) {
            console.log("Successfully voted");
            showYouVotedBanner(teamId);
        },
        error: function(error) {
            console.log("Error: "+ JSON.stringify(error));
            alert("Hmm, there was an error");
        }
    });
}

function showYouVotedBanner(teamId) {
    var teamVotedBannerId = "#thanks-for-voting-"+teamId;
    $(teamVotedBannerId).fadeIn(600);

    setInterval(function () {
        $(teamVotedBannerId).fadeOut(1000);
    }, 5000);
}