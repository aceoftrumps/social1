function fillProfileData() {
    var requestParamName = getQueryParam('name');
    var url = typeof requestParamName === 'undefined' ? "profiledata" : "profiledata?name=" + requestParamName;

    $.get(url, function (profile) {
        $('#profileName').html(profile.user.name);
        $('#firstName').html(profile.user.firstname);
        $('#lastName').html(profile.user.lastname);
        $('#sex').html("sex: " + profile.user.sex);
        $('#age').html("age: " + profile.user.age);
        $('#interests').html(profile.user.interests);
        $('#city').html(profile.user.city);

        var userTitle = !profile.ownProfile ? "actions" : "users to follow";
        $("#nonFriendsList").html('<h5 class="card-title">' + userTitle + '</h5>');

        fillUserList("#friendsList", profile.friends, profile.ownProfile, profile.friendOfOwnProfile, requestParamName, true);
        fillUserList("#nonFriendsList", profile.nonFriends, profile.ownProfile, profile.friendOfOwnProfile, requestParamName, false);

        fillNews('myNewsList', profile.myNews);
        fillNews('friendsNewsList', profile.friendsNews);
    });
};

function getQueryParam(param, defaultValue = undefined) {
    location.search.substr(1)
        .split("&")
        .some(function(item) { // returns first occurence and stops
            return item.split("=")[0] == param && (defaultValue = item.split("=")[1], true)
        })
    return defaultValue
}

function fillUserList(elementId, users, isOwnProfile, isFriendOfOwnProfile, profileName, isFriend) {
    var id = "frinedList" + isFriend;
    $('#' + id).remove();

    if (showNonUserList(isFriend, isOwnProfile)) {
        var sub_ul = $('<ul/>', {id: id});
        sub_ul.addClass("list-group list-group-flush");
        $.each(users, function (index, user) {
            var sub_li = $('<li/>');
            sub_li.addClass("list-group-item");


            var link = $('<a/>', {
                text: user.name,
                title: user.name,
                href: '/profile?name=' + user.name,

            });
            sub_li.append(link);
            sub_li.append("&nbsp;");

            if (isOwnProfile) {
                sub_li.append(createFollowLink(isFriend, user.name));
            }

            sub_ul.append(sub_li);
        });
        $(elementId).append(sub_ul);
    }
    else{
        $(elementId).append(createFollowLink(isFriendOfOwnProfile, profileName));
    }
}

function createFollowLink(isFriend, userName){
    return $('<a/>', {
        text: isFriend ? "unfollow" : "follow",
        title: isFriend ? "unfollow" : "follow",
        href: '#',
        click: function () {
            follow(!isFriend, userName);
        }
    });
}

function showNonUserList(isFriend, isOwnProfile){
    return isFriend || isOwnProfile;
}

function follow(isFollow, name){
    var url = isFollow ? "follow" : "unfollow";

    $.post(url, { name: name })
        .done(function() {
            fillProfileData();
        });
}

function addNews(){
    var textArea = $('#addNewsTextarea');

    $.post("news", { value: textArea.val() })
        .done(function() {
            fillProfileData();
            textArea.val("");
        });

}

function fillNews(elementId, news){
    let id = elementId + "sub";
    $('#' + id).remove();

    var sub_ul = $('<ul/>', {id: id});
    sub_ul.addClass("list-group list-group-flush");
    $.each(news, function (index, newsItem) {
        var sub_li = $('<li/>');
        sub_li.addClass("list-group-item");


        sub_li.append(newsItem);
        sub_li.append("&nbsp;");

        sub_ul.append(sub_li);
    });
    $('#' + elementId).append(sub_ul);

}