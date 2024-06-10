import React from 'react';
import cl from './Logo.module.css'
import {Link} from "react-router-dom";

const Logo = ({onClick}) => {
    return (
        <Link onClick={onClick} className={cl.logoText} to="/auctions">
            Auction Hub
        </Link>
    );
};

export default Logo;