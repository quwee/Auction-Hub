import React, {useState} from 'react';
import {AuthContext} from "./context";

const AuthContextProvider = ({children}) => {

    const [isAuth, setIsAuth] = useState(() => {
        const token = localStorage.getItem("accessToken")
        return token !== null
    })

    const [user, setUser] = useState(() => {
        const userFromStorage = localStorage.getItem("user")

        console.log('AuthContextProvider:useState (user): userFromStorage:', userFromStorage)

        let loggedInUser = null

        try {
            loggedInUser = JSON.parse(userFromStorage)
        } catch (e) {
            console.log('AuthContextProvider: useState (user): error:', e)
            localStorage.clear()
        }

        return loggedInUser
    })

    return (
        <AuthContext.Provider value={{isAuth, setIsAuth, user, setUser}}>
            {children}
        </AuthContext.Provider>
    );
};

export default AuthContextProvider;