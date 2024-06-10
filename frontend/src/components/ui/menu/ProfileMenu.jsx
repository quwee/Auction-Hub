import React from 'react';
import cl from './ProfileMenu.module.css'
import {Link, useNavigate} from "react-router-dom";
import ProfileIcon from "../../../assets/profile.svg"
import ListIcon from "../../../assets/list.svg"

const ProfileMenu = ({isOpen}) => {

    const navigate = useNavigate()

    const click = e => {
        e.stopPropagation()
    }

    return (
        <div className={`${cl.menu} ${isOpen ? cl.active : ''}`} onClick={click}>
            <div className={cl.menu__items}>
                <div
                    className={cl.menu__item__container}
                    onClick={e => navigate('/profile')}>
                    <div className={cl.menu__item__image__container}>
                        <img className={cl.menu__item__image} src={ProfileIcon} alt=""/>
                    </div>
                    <div className={cl.menu__item__link__container}>
                        <span className={cl.menu__item}>Профиль</span>
                    </div>
                </div>

                {/*<div
                    className={cl.menu__item__container}
                    onClick={e => navigate('/user-auctions')}>
                    <div className={cl.menu__item__image__container}>
                        <img className={cl.menu__item__image} src={ListIcon} alt=""/>
                    </div>
                    <div className={cl.menu__item__link__container}>
                        <span className={cl.menu__item}>Ваши аукционы</span>
                    </div>
                </div>*/}
            </div>
        </div>
    );
};

export default ProfileMenu;