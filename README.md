# Poison Ivy Detector

This is an educational project to hone and demonstrate of machine learning skills.

The goal for the final product is an Android application running a TFLite model which can determine whether or not there is poison ivy present in the camera feed, such that you can avoid walking into such areas (i.e. when looking for your golf ball in the woods, as I always am).

The followng are the steps included:

## 1. Dataset Acquisition (IN PROGRESS)

The current plan is to utilize a google image downloading webscraping tool to find appropriate images to establish a dataset:

1. Use the google_image_download package to download images based on a poison ivy search & verify it works
2. Filter out bad images:
   - Images with lots of texts or overlays (run an image text detector?)
   - Images without the plant in it, i.e. a person showing a rash (check average green-ness / brown-ness in color channels?)
3. Get labelled counter examples. Current ideas:
   - Compare to similar looking plants based on online search, also using google_image_download
   - Generic non-poison ivy plant pictures (maybe plantnet?)
   - Compare to random general pics of anything (so the model confidently says no poison ivy if you aren't in the woods for example)
   - A weighted average of the above?
4. Move & split images into appropriate directories for Tensorflow Image Data Generator

At this point in time, I can't imagine a scalable way to obtain bounding boxes or image segmentation labels for this dataset so I will stick to binary is/isn't present labelling.

## 2. Model Development (TO DO)

Use Tensorflow to develop a supervised model for poison ivy detection:

1. Create a dataset generator from the image directories
2. Create model structure
   - Custom CNN
   - Fine-tuning an existing model (MobileNet?)
3. Tuning and iterating model parameters, image augmentation, etc.
4. Save final model and export a Tensorflow-lite converted version

## 3. App implementation (TO DO)

Since I am not an Android developer, the goal is to produce the simplest working proof-of-concept which:

1. Imports the Tensorflow-lite model
2. Converts camera-feed image to appropriate data type and feeds to model
3. Has a live display of results

<br />

## Implementation Notes

I currently plan to establish a Docker-compose file for creating a Tensorflow + scipy development environment. I will initially try to get to a pilot model going on limited data & Jupyter notebook coding with the potential to expand and refine in the future.
