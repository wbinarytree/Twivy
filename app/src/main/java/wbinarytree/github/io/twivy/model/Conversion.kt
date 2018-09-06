package wbinarytree.github.io.twivy.model

import com.twitter.sdk.android.core.models.Tweet
import com.twitter.sdk.android.core.models.User

fun Tweet.toDb(): TweetDB {
    return TweetDB(
        id,
        createdAt,
        text,
        user.toUser()
    )
}

fun User.toUser(): wbinarytree.github.io.twivy.model.User {
    return wbinarytree.github.io.twivy.model.User(
        contributorsEnabled,
        createdAt,
        defaultProfile,
        defaultProfileImage,
        description,
        email,
        entities,
        favouritesCount,
        followRequestSent,
        followersCount,
        friendsCount,
        geoEnabled,
        id,
        idStr,
        isTranslator,
        lang,
        listedCount,
        location,
        name,
        profileBackgroundColor,
        profileBackgroundImageUrl,
        profileBackgroundImageUrlHttps,
        profileBackgroundTile,
        profileBannerUrl,
        profileImageUrl,
        profileImageUrlHttps,
        profileLinkColor,
        profileSidebarBorderColor,
        profileSidebarFillColor,
        profileTextColor,
        profileUseBackgroundImage,
        protectedUser,
        screenName,
        showAllInlineMedia,
        null,
        statusesCount,
        timeZone,
        url,
        utcOffset,
        verified,
        withheldInCountries,
        withheldScope
    )
}