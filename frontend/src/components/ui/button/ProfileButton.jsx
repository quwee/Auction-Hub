import React from 'react';
import ProfileIcon from "../../../assets/profile.png";
import {Link} from "react-router-dom";

const ProfileButton = ({style}) => {
    return (
        <Link style={style} to="/profile">
            <img src={ProfileIcon} alt="profile"/>
        </Link>
    );
};

export default ProfileButton;