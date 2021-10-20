FROM tensorflow/tensorflow:latest-gpu-py3

# Intended for attaching VS code container and executing a Python environment

WORKDIR /home
RUN mkdir code
COPY ./requirements.txt .
RUN pip install --upgrade pip
RUN pip install -r requirements.txt

CMD tail -f /dev/null
