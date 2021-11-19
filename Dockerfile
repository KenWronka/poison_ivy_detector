FROM tensorflow/tensorflow:2.6.0-gpu-jupyter

# Intended for attaching VS code container and executing a Python environment

RUN apt-get update
RUN apt update && apt install -y libsm6 libxext6
RUN apt-get install -y tesseract-ocr libtesseract-dev libleptonica-dev
# RUN apt-get -y install tesseract-ocr
# RUN apt install -y libgl1-mesa-glx
WORKDIR /home
RUN mkdir code
RUN pip install --upgrade pip
RUN pip install Pillow
COPY ./requirements.txt .
RUN pip install -r requirements.txt
CMD jupyter lab --ip=0.0.0.0 --port=8888 --allow-root --NotebookApp.token='' --NotebookApp.password=''
# CMD tail -f /dev/null
