import React from 'react';
import {BrowserRouter} from "react-router-dom";
import "./styles/App.css"
import AppRouter from "./components/AppRouter";
import AuthContextProvider from "./context/AuthContextProvider";

function App() {

    return (
        <AuthContextProvider>
            <BrowserRouter>
                <AppRouter/>
            </BrowserRouter>
        </AuthContextProvider>
    );
}

export default App;
