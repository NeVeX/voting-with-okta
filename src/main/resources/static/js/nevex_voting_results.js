//
// function grandPrizeButtonClicked() {
//     changeHeaderTitle("Grand Prize");
//     showResults();
// }
//
//
//
// function mostCreativeButtonClicked() {
//     changeHeaderTitle("Most Creative");
//
// }
//
//
// function mostImpactfulButtonClicked() {
//     changeHeaderTitle("Most Impactful");
//
// }

// function changeHeaderTitle(title) {
//     $("#header-title").text(title);
// }

$( document ).ready(function() {
    setTimeout(showResults, 2000);
});


function showResults() {
    var index = parseInt($("meta[name='results_size']").attr("content")) - 1;
    var showResultsInterval = setInterval(function () {

        if ( index < 0) {
            clearInterval(showResultsInterval);
        }

        var teamNameElem = $("#team-name-"+index);
        var teamVoteElem = $("#team-votes-"+index);
        if ( teamNameElem && teamVoteElem) {
            var teamNameToShow = teamNameElem.attr('data-teamName');
            var teamVotesToShow = teamVoteElem.attr('data-teamVotes');
            teamNameElem.text(teamNameToShow);
            teamVoteElem.text(teamVotesToShow+" Votes");
        }
        index--;

    }, 2300);

}