import React, {useState} from 'react';
import {useDropzone} from 'react-dropzone';
import cl from './ImageDropZone.module.css';

const ImageDropZone = ({images, setImages}) => {

    const maxFiles = 3
    const onDrop = (acceptedFiles) => {
        let count = images.length + acceptedFiles.length
        if (maxFiles - count < 0) {
            return
        }
        acceptedFiles.forEach((file) => {
            setImages((prevState) => [...prevState, file])
        })
    }

    const {
        getRootProps,
        getInputProps,
        isDragActive,
    } = useDropzone({
        onDrop,
        accept: {
            'image/png': [],
            'image/jpeg': [],
        },
        maxFiles: maxFiles
    })

    const handleImageRemove = (index) => {
        setImages((prevImages) => prevImages.filter((_, i) => i !== index))
    }

    return (
        <div className={cl.container}>
            <div className={cl.image__container}>
                {images.length > 0 &&
                    images.map((image, index) => (
                        <div className={cl.image__item} key={image.name}>
                            <span className={cl.image__item__remove} onClick={() => handleImageRemove(index)}>
                                &#x2716;
                            </span>
                            <img src={`${URL.createObjectURL(image)}`} key={index} alt=""/>
                        </div>
                    ))}
            </div>
            <div
                className={images.length < maxFiles ? cl.dropzone : cl.hidden}
                {...getRootProps()}>
                <input {...getInputProps()} />
                {isDragActive ? (
                    <p>Перетащите сюда ...</p>
                ) : (
                    <p>Выберите файлы...</p>
                )}
            </div>
        </div>
    );
};

export default ImageDropZone;