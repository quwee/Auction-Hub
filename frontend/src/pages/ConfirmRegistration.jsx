import React, {useState} from 'react';
import cl from '../styles/ConfirmRegistration.module.css'
import {useLocation, useNavigate} from "react-router-dom";
import AuthService from "../API/service/AuthService";

const ConfirmRegistration = () => {

    const [confirmError, setConfirmError] = useState('ERROR')
    const [isConfirmError, setIsConfirmError] = useState(false)

    const location = useLocation()
    const navigate = useNavigate()

    const confirmRegistration = async () => {
        const searchParams = new URLSearchParams(location.search)
        const token = searchParams.get('token')

        if (!token) {
            return
        }

        let response = await AuthService.confirmRegistration({
            'verificationToken': token
        })

        if (response.status === 200) {
            navigate('/login')
        } else if (response.status === 404 || response.status === 409 || response.status === 500) {
            setConfirmError(response.data.message)
            setIsConfirmError(true)
        } else {
            console.log('error:', response)
            setConfirmError('Ошибка сервера')
            setIsConfirmError(true)
        }
    }

    return (
        <div className={cl.container}>
            <div className={cl.content}>
                <p className={cl.title}>Подтвердите регистрацию</p>
                <p className={cl.error}
                    style={{visibility: isConfirmError ? 'visible' : 'hidden'}}>
                    {confirmError}
                </p>
                <button className={cl.button} onClick={confirmRegistration}>Подтвердить</button>
            </div>
        </div>
    );
};

export default ConfirmRegistration;