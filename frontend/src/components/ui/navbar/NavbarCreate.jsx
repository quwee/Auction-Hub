import React from 'react';
import cl from "./Navbar.module.css";
import Logo from "../logo/Logo";
import BurgerButton from "../button/BurgerButton";

const NavbarCreate = ({onBurgerButtonClick, isMenuOpen}) => {
    return (
        <nav className={cl.navbar}>
            <Logo/>
            <div className={cl.navbar__links}>
                <BurgerButton onClick={onBurgerButtonClick} isMenuOpen={isMenuOpen}/>
            </div>
        </nav>
    );
};

export default NavbarCreate;