package altayiskender.movieapp.Pojos

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(tableName = "bookmarks")
data class Movie(
        @PrimaryKey
        @ColumnInfo(name = "id")
        var id: Long?=0,
        @SerializedName("vote_count")
        @Ignore
        var voteCount: Int?,
        @SerializedName("vote_average")
        @Ignore
        var voteAverage: Double?,
        @Ignore
        var runtime: Int?,
        var title: String?="",
        @SerializedName("release_date")
        var releaseDate: String?="",
        @SerializedName("poster_path")
        var posterPath: String?="",
        @SerializedName("backdrop_path")
        @Ignore
        var backdropPath: String?,
        @Ignore
        var genres: List<Genre>?,
        @Ignore
        var overview: String?,
        @Ignore
        var credits: Credits?,
        @Ignore
        var videos: VideosPage?
) : Serializable{
        constructor():this(0,0,0.0,0,"","","","",null,null,null,null)
}