import React, {useEffect, useState} from 'react';
import Navbar from "./ui/navbar/Navbar";
import ProfileMenu from "./ui/menu/ProfileMenu";

const NavbarWithMenu = () => {
    const [isMenuOpen, setIsMenuOpen] = useState(false)

    const toggleMenu = () => {
        setIsMenuOpen(!isMenuOpen)
    }

    const handleClick = () => {
        setIsMenuOpen(false)
    }

    useEffect(() => {
        document.addEventListener('click', handleClick);
        return () => {
            document.removeEventListener('click', handleClick);
        }
    }, [])

    return (
        <div>
            <Navbar
                onBurgerButtonClick={toggleMenu}
                isMenuOpen={isMenuOpen}/>
            <ProfileMenu isOpen={isMenuOpen}/>
        </div>
    );
};

export default NavbarWithMenu;