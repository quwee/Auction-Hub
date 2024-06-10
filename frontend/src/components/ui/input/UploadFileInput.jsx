import React, {useState} from 'react';

const UploadFileInput = () => {

    const [selectedFile, setSelectedFile] = useState(null)
    const [files, setFiles] = useState([])
    const [uploadButtonDisabled, setUploadButtonDisabled] = useState(false)

    const handleUploadFile = (e) => {
        if (!e.target.files[0]) {
            return
        }
        let list = files
        list.push(e.target.files[0].name)
        setSelectedFile(e.target.files)
        setFiles(list)

        if(list.length === 3) {
            setUploadButtonDisabled(true)
        }

        console.log(list)
    }

    return (
        <div>
            <input type='file'
                   formEncType='multipart/form-data'
                   onChange={handleUploadFile}
                   multiple
                   disabled={uploadButtonDisabled}
            />
            <div>
                {files.map(item => {
                    return (<a href='#' key={item.toString()}>{item}</a>);
                })}
            </div>
        </div>
    );
};

export default UploadFileInput;