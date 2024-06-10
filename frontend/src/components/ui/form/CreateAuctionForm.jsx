import React, {useEffect, useState} from 'react';
import cl from './CreateAuctionForm.module.css'
import {DateTimePicker} from "react-datetime-picker";
import 'react-datetime-picker/dist/DateTimePicker.css';
import 'react-calendar/dist/Calendar.css';
import 'react-clock/dist/Clock.css';
import 'react-datepicker/dist/react-datepicker.css'
import ImageDropZone from "../drop-zone/ImageDropZone";
import checkPriceInput from "../../../util/checkPrice";
import AuctionService from "../../../API/service/AuctionService";
import {useNavigate} from "react-router-dom";
import moment from 'moment';
import {DATE_TIME_PATTERN} from "../../../util/constants";
import Loader from "../loader/Loader";

const CreateAuctionForm = () => {

    const minDate = new Date()
    const maxDate = new Date()
    minDate.setTime(minDate.getTime() + 24 * 60 * 60 * 1000)
    maxDate.setTime(minDate.getTime() + 7 * 24 * 60 * 60 * 1000)


    const [name, setName] = useState('')
    const [startPrice, setStartPrice] = useState('')
    const [priceStep, setPriceStep] = useState('')
    const [endDate, setEndDate] = useState(minDate)
    const [desc, setDesc] = useState('')
    const [images, setImages] = useState([])

    const [nameError, setNameError] = useState('ERROR')
    const [startPriceError, setStartPriceError] = useState('ERROR')
    const [priceStepError, setPriceStepError] = useState('ERROR')
    const [endDateError, setEndDateError] = useState('ERROR')
    const [descError, setDescError] = useState('ERROR')
    const [imageError, setImageError] = useState('ERROR')

    const [isNameError, setIsNameError] = useState(false)
    const [isStartPriceError, setIsStartPriceError] = useState(false)
    const [isPriceStepError, setIsPriceStepError] = useState(false)
    const [isEndDateError, setIsEndDateError] = useState(false)
    const [isDescError, setIsDescError] = useState(false)
    const [isImageError, setIsImageError] = useState(false)

    const [formattedDate, setFormattedDate] = useState(moment(endDate).format(DATE_TIME_PATTERN))
    const [isLoading, setIsLoading] = useState(false)


    const navigate = useNavigate()

    const validateFields = () => {
        let isErrors = false

        if (!name) {
            setNameError('Заполните поле')
            setIsNameError(true)
            isErrors = true
        } else {
            setIsNameError(false)
        }

        if (!startPrice) {
            setStartPriceError('Заполните поле')
            setIsStartPriceError(true)
            isErrors = true
        } else {
            setIsStartPriceError(false)
        }

        if (!priceStep) {
            setPriceStepError('Заполните поле')
            setIsPriceStepError(true)
            isErrors = true
        } else {
            setIsPriceStepError(false)
        }

        if (!endDate) {
            setEndDateError('Заполните поле')
            setIsEndDateError(true)
            isErrors = true
        } else {
            setIsEndDateError(false)
        }

        if (!desc) {
            setDescError('Заполните поле')
            setIsDescError(true)
            isErrors = true
        } else {
            setIsDescError(false)
        }

        if (images.length === 0) {
            setImageError('Выберите фото')
            setIsImageError(true)
            isErrors = true
        } else {
            setIsImageError(false)
        }

        return isErrors
    }

    const handleCreateButtonClick = async () => {
        let isErrors = validateFields();
        if(isErrors) {
            return
        }

        setIsLoading(true)

        console.log('formattedDate:', formattedDate)

        let requestDto = {
            'lotName': name,
            'minPrice': startPrice,
            'priceStep': priceStep,
            'endDate': formattedDate,
            'lotDesc': desc
        }

        const jsonStr = JSON.stringify(requestDto)
        const blob = new Blob([jsonStr], {
            type: 'application/json'
        });

        const formData = new FormData()

        formData.append('requestDto', blob)
        images.forEach(image => formData.append('images', image))

        console.log('formData:', formData)

        let response = await AuctionService.createAuction(formData)

        if (!response) {
            console.log('error', response)
            alert('Сервер не отвечает')
            setIsLoading(false)
            return
        }

        if (response.status === 200) {
            navigate('/auctions')
        } else if (response.status === 400) {
            if(!response.data.errors) {
                console.log(response.data.message)
                return
            }
            response.data.errors.forEach(error => {
                if (error.field === 'lotName') {
                    setNameError(error.message)
                    setIsNameError(true)
                }
                if (error.field === 'minPrice') {
                    setStartPriceError(error.message)
                    setIsStartPriceError(true)
                }
                if (error.field === 'priceStep') {
                    setPriceStepError(error.message)
                    setIsPriceStepError(true)
                }
                if (error.field === 'endDate') {
                    setEndDateError(error.message)
                    setIsEndDateError(true)
                } else {
                    setEndDateError(error.message)
                    setIsEndDateError(true)
                }
                if (error.field === 'lotDesc') {
                    setDescError(error.message)
                    setIsDescError(true)
                }
                if (error.field === 'images') {
                    setImageError(error.message)
                    setIsImageError(true)
                }
            })
        } else {
            console.log('error', response)
            alert('Ошибка сервера')
        }
        setIsLoading(false)
    }

    return (
        <div className={cl.container}>
            <p className={cl.title}>Создать аукцион</p>
            <div>
                <p className={cl.label}>Название товара</p>
                <input
                    className={cl.input}
                    value={name}
                    onChange={e => setName(e.target.value)}
                    type="text"
                    placeholder="Введите название..."
                    autoComplete="off"/>
                <p hidden={!isNameError} className={cl.error__message}>{nameError}</p>
            </div>
            <div>
                <p className={cl.label}>Начальная цена, $</p>
                <input
                    className={cl.input}
                    value={startPrice}
                    onChange={e => checkPriceInput(e, 7, setStartPrice)}
                    type="text"
                    placeholder="Введите цену..."
                    autoComplete="off"/>
                <p hidden={!isStartPriceError} className={cl.error__message}>{startPriceError}</p>
            </div>
            <div>
                <p className={cl.label}>Шаг ставки, $</p>
                <input
                    className={cl.input}
                    value={priceStep}
                    onChange={e => checkPriceInput(e, 6, setPriceStep)}
                    type="text"
                    placeholder="Введите шаг..."
                    autoComplete="off"/>
                <p hidden={!isPriceStepError} className={cl.error__message}>{priceStepError}</p>
            </div>
            <p className={cl.label}>Дата окончания</p>
            <DateTimePicker
                className={cl.date}
                value={endDate}
                onChange={date => setEndDate(date)}
                minDate={minDate}
                maxDate={maxDate}/>
            <p hidden={!isEndDateError} className={cl.error__message}>{endDateError}</p>
            <div>
                <p className={cl.label}>Описание товара</p>
                <textarea
                    className={[cl.input, cl.desc].join(' ')}
                    value={desc}
                    onChange={e => setDesc(e.target.value)}
                    placeholder="Введите описание..."
                    rows="6"/>
                <p hidden={!isDescError} className={cl.error__message__desc}>{descError}</p>
            </div>
            <ImageDropZone images={images} setImages={setImages}/>
            <p hidden={!isImageError} className={cl.error__message}>{imageError}</p>
            <div className={cl.button__container}>
                <button className={cl.button} onClick={handleCreateButtonClick}>Создать</button>
            </div>
            <div style={{display: isLoading ? 'flex' : 'none'}} className={cl.modal__container}>
                <Loader/>
            </div>
        </div>
    );
};

export default CreateAuctionForm;