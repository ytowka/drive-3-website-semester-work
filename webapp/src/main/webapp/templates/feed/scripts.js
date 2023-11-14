window.onscroll = function() {
    //console.log(`onScroll ${window.innerHeight} + ${window.scrollY} >= ${document.body.offsetHeight}`)
    if ((window.innerHeight + window.scrollY) >= document.body.offsetHeight - 100) {
        loadPosts('feed-api');
    }
};


loadPosts('feed-api');