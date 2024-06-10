import React, {useContext, useEffect} from 'react';
import {AuthContext, useAuthContext} from "../context/context";
import {Navigate, Redirect, Route, Routes} from "react-router-dom";
import Auctions from "../pages/Auctions";
import Auction from "../pages/Auction";
import CreateAuctions from "../pages/CreateAuctions";
import Profile from "../pages/Profile";
import NotFound from "../pages/NotFound";
import Login from "../pages/Login";
import SignUp from "../pages/SignUp";
import ConfirmRegistration from "../pages/ConfirmRegistration";
import Forbidden from "../pages/Forbidden";

const AppRouter = () => {
    const {isAuth} = useAuthContext();

    return (
            <Routes>
                <Route path="/" element={<Navigate replace to="/auctions"/>}/>
                <Route path="/registration" element={<SignUp/>}/>
                <Route path="/login" element={<Login/>}/>
                <Route path="/confirm-registration" element={<ConfirmRegistration/>}/>
                <Route path="/auctions" element={<Auctions/>}/>
                <Route path="/auctions/:id" element={<Auction/>}/>

                <Route path="/create-auction" element={isAuth ? <CreateAuctions/> : <Forbidden/>}/>
                <Route path="/profile" element={isAuth ? <Profile/> : <Forbidden/>}/>

                <Route path="*" element={<NotFound/>}/>
            </Routes>
    );
};

export default AppRouter;