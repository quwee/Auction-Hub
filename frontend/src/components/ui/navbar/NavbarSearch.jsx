import React, {useContext, useState} from 'react';
import {AuthContext} from "../../../context/context";
import Logo from "../logo/Logo";
import LoginButton from "../button/LoginButton";
import cl from "./NavbarSearch.module.css"
import SearchInput from "../input/SearchInput";
import BurgerButton from "../button/BurgerButton";
import {Link} from "react-router-dom";

const NavbarSearch = ({onLogoClick, onSearchButtonClick, onBurgerButtonClick, isMenuOpen}) => {

    const {user} = useContext(AuthContext)
    const [query, setQuery] = useState('')

    return (
        <nav className={cl.navbar}>
            <Logo onClick={onLogoClick}/>
            <div className={cl.navbar__search__create__container}>
                <SearchInput
                    query={query}
                    setQuery={setQuery}
                    onSearchButtonClick={onSearchButtonClick}/>
                <Link to={user ? "/create-auction" : "/login"} className={cl.navbar__create__button}/>
            </div>
            {user
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

export default NavbarSearch;