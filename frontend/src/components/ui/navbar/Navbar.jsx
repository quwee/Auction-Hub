import React, {useContext} from 'react';
import cl from "./Navbar.module.css";
import Logo from "../logo/Logo";
import BurgerButton from "../button/BurgerButton";
import LoginButton from "../button/LoginButton";
import {AuthContext} from "../../../context/context";

const Navbar = ({onBurgerButtonClick, isMenuOpen}) => {

    const {isAuth} = useContext(AuthContext)

    return (
        <nav className={cl.navbar}>
            <Logo/>
            {isAuth
                ?
                <div className={cl.navbar__links}>
                    <BurgerButton onClick={onBurgerButtonClick} isMenuOpen={isMenuOpen}/>
                </div>
                :
                <div className={cl.navbar__links}>
                    <LoginButton/>
                </div>
            }
        </nav>
    );
};

export default Navbar;