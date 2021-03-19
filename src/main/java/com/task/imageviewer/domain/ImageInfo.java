package com.task.imageviewer.domain;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity(name = "images")
public class ImageInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name", unique = true)
    private String name;

    @Column(name = "uploaded")
    private Date uploaded;

    public ImageInfo() {
    }

    public ImageInfo(String name) {
        this.name = name;
        this.uploaded = new Date();
    }

    public ImageInfo(Long id, String name, Date uploaded) {
        this.id = id;
        this.name = name;
        this.uploaded = uploaded;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Date getUploaded() {
        return uploaded;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ImageInfo imageInfo = (ImageInfo) o;

        if (!Objects.equals(id, imageInfo.id)) return false;
        if (!Objects.equals(name, imageInfo.name)) return false;
        return Objects.equals(uploaded, imageInfo.uploaded);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (uploaded != null ? uploaded.hashCode() : 0);
        return result;
    }
}
