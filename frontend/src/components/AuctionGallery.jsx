import React, {useEffect, useState} from 'react'
import cl from "../styles/AuctionGallery.module.css"
import api from "../API/api"

const AuctionGallery = ({imgNames}) => {
    
    const [gallery, setGallery] = useState()
    const [imageUrls, setImageUrls] = useState([])
    const [selectedImage, setSelectedImage] = useState()

    const fetchImages = async () => {
        try {
            for (let i = 0; i < imgNames.length; i++) {
                const response = await api.get(`auction/load-image/${imgNames[i]}`, {
                    responseType: 'arraybuffer',
                })

                const arrayBuffer = response.data
                const byteArray = new Uint8Array(arrayBuffer)
                const base64Image = btoa(String.fromCharCode.apply(null, byteArray))
                const imageDataUrl = `data:image/jpeg;base64,${base64Image}`

                setImageUrls(prevUrls => [...prevUrls, imageDataUrl])
            }
        } catch (error) {
            console.error('Error fetching image:', error)
        }
    }

    useEffect(() => {
        const fetch = async () => {
            await fetchImages()
        }
        fetch()
    }, [])

    useEffect(() => {
        setGallery(renderGallery())
    }, [imageUrls])


    const handleImageClick = (image) => {
        setSelectedImage(image)
    }

    const handleBackdropClick = () => {
        setSelectedImage(null)
    }

    const renderGallery = () => {
        let len = imageUrls.length

        if (len === 1) {
            return (
                <div className={cl.container1}>
                    <div
                        className={cl.img__container1}
                        onClick={() => handleImageClick(imageUrls[0])}
                        key={imgNames[0]}>
                        <img className={cl.img} src={imageUrls[0]} alt={`imageUrls[0]`}/>
                    </div>
                </div>
            )
        } else if (len === 2) {
            return (
                <div className={cl.container2}>
                    {imageUrls.map((item, id) =>
                        <div
                            className={cl.img__container2}
                            onClick={() => handleImageClick(imageUrls[id])}
                            key={imgNames[id]}>
                            <img className={cl.img} src={item} alt=""/>
                        </div>
                    )}

                </div>
            )
        } else {
            return (
                <div className={cl.container3}>
                    <div
                        className={cl.img__container3}
                        onClick={() => handleImageClick(imageUrls[0])}
                        key={imgNames[0]}>
                        <img className={cl.img3} src={imageUrls[0]} alt=""/>
                    </div>
                    <div
                        onClick={() => handleImageClick(imageUrls[1])}
                        key={imgNames[1]}>
                        <img className={cl.img3} src={imageUrls[1]} alt=""/>
                    </div>
                    <div
                        onClick={() => handleImageClick(imageUrls[2])}
                        key={imgNames[2]}>
                        <img className={cl.img3} src={imageUrls[2]} alt=""/>
                    </div>
                </div>
            )
        }
    }

    return (
        <div className={cl.container}>
            {gallery}
            {selectedImage && (
                <div className={`${cl.backdrop} ${cl.open}`} onClick={handleBackdropClick}>
                    <div className={`${cl.modal} ${cl.open}`}>
                        <img className={cl.modalImage} src={selectedImage} alt="" />
                    </div>
                </div>
            )}
        </div>
    )
}

export default AuctionGallery