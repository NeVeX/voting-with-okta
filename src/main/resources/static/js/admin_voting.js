
function openVoting() {
    openOrCloseVoting("openvoting");
}

function closeVoting() {
    openOrCloseVoting("closevoting");
}

function openOrCloseVoting(votingChange) {

    $.ajax({
        type: "POST",
        url: "admin/"+votingChange,
        headers: getHeadersForApiPostCall(),
        success: function(data) {
            console.log("Successfully changed vote for "+votingChange);
            alert("Successfully changed voting to "+votingChange);
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
