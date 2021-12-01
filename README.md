# Poison Ivy Detector - Overview

• Assembled dataset of around 10,000 images via web-scraping & API calls with metadata managed in Postgres database accross steps

• Filtered out bad images by devising and applying custom filter functions that capture undesired traits

• Tuned a MobileNetV3 model to classify images using transfer learning & fine-tuning in Tensorflow

• Exported model with TF Lite and adapted into a simple Android app to detect poison ivy in camera feed.

---

# Implementation Details

The end goal for this project is an app that can distinguish whether or not the camera feed contains poison ivy. This project is built for educational purposes to explore and demonstrate modern data science techniques.

To view similar projects, check out my Data Science portfolio page (#TODO).

As this is for educational purposes, I am keeping old and "pilot" versions of the code; [the most recent version of the model steps code can be found here](./code/model_pipeline_v1).

## Creating the development environment

(#TODO)

## Assembling the dataset

(#TODO)
![picture examples](./GH_images/pic_examples.png)

## Filtering the images

(#TODO)
![filter examples](./GH_images/filter_examples.png)
![filter counts](./GH_images/filter_chart.png)

## Splitting the dataset

(#TODO)

## Fitting the model

(#TODO)
![model training](./GH_images/training_progress.png)
![confusion matrix multiclass](./GH_images/cm_multi_multi.png)
![confusion matrix binary](./GH_images/cm_multi_binary.png)

## Implementing as an app

(#TODO)
![app image](./GH_images/app_screenshot.png)

## Limitations & Future Possibilities

(#TODO)
