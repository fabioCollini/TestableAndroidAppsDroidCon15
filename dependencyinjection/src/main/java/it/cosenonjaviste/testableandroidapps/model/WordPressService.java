package it.cosenonjaviste.testableandroidapps.model;

import retrofit.http.GET;

public interface WordPressService {
    @GET("/?json=get_recent_posts&count=10&exclude=attachments,thumbnail_images,content,title_plain,tags,custom_fields") PostResponse listPosts();
}