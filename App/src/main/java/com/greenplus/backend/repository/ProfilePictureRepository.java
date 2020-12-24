package com.greenplus.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.greenplus.backend.model.ProfilePicture;
import com.greenplus.backend.model.User;

@Repository
public interface ProfilePictureRepository extends JpaRepository<ProfilePicture, Integer> {

	ProfilePicture findByUser(User user);

}
