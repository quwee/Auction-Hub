import React from 'react';
import cl from "./LoginButton.module.css";
import {Link} from "react-router-dom";

const LoginButton = () => {
    return (
            <Link className={cl.loginBtn} to="/login">
                Войти
            </Link>
    );
};

export default LoginButton;