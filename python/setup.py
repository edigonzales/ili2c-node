import os
from setuptools import setup, find_packages

with open('README.md') as readme_file:
    readme = readme_file.read()

setup(
    author="Stefan Ziegler",
    author_email='edi.gonzales@gmail.com',
    python_requires='>=3.8',
    classifiers=[
        "Programming Language :: Python :: 3",
        "License :: OSI Approved :: MIT License",
        'Operating System :: POSIX :: Linux',
        'Operating System :: Microsoft :: Windows',
        'Operating System :: MacOS',
    ],
    description="Python package for ili2c.",
    install_requires=['importlib-resources'] ,
    license="MIT license",
    long_description=readme,
    long_description_content_type="text/markdown",
    include_package_data=True,
    keywords='ili2c,interlis',
    name='ili2c',
    packages=find_packages(include=['ili2c', 'ili2c.*']),
    #package_data={'ili2c.lib_ext':['*.h', '*.lib', '*.dll', '*.so', '*.dylib']},
    package_data={'ili2c':['lib_ext/*.h', 'lib_ext/*.lib', 'lib_ext/*.dll', 'lib_ext/*.so', 'lib_ext/*.dylib']},
    #test_suite='tests',
    #tests_require=test_requirements,
    url='https://github.com/edigonzales/ili2c-node',
    version='0.0.1',
)